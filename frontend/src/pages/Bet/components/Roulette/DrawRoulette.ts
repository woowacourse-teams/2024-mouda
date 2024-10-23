const getCanvasUtil = (
  canvas: HTMLCanvasElement,
  widthPx?: number,
  heightPx?: number,
) => {
  return {
    /**
     * getPercentToWidth
     * @param percent 1~100
     * @param canvas
     * @returns percentNumber
     */

    gpw: (percent: number) => {
      const width = widthPx ?? canvas.width;
      percent = Math.min(percent, width);

      return (percent / 100) * width;
    },

    /**
     * getPercentToHeight
     * @param percent 1~100
     * @param canvas
     * @returns percentNumber
     */
    gph: (percent: number) => {
      const height = heightPx ?? canvas.height;
      percent = Math.min(percent, height);

      return (percent / 100) * height;
    },
  };
};

interface SlotItem {
  name: string;
  drawStartHeightPercent: number;
}

// 페이즈 : 돌아가기(SPIN_PHASE)->잡기(CATCH_PHASE)->패배자에 맞게 조정(ADJUST_PHASE)
const SPIN_PHASE = Symbol('SPIN_PHASE');
const CATCH_PHASE = Symbol('CATCH_PHASE');
const ADJUST_PHASE = Symbol('ADJUST_PHASE');
const FINISH_PHASE = Symbol('FINISH_PHASE');

type SlotPhase =
  | typeof SPIN_PHASE
  | typeof CATCH_PHASE
  | typeof ADJUST_PHASE
  | typeof FINISH_PHASE;

class SlotItemMover {
  nowList: SlotItem[];
  #slotSize;
  #itemHeightPercent;

  constructor(nameList: string[], itemHeightPercent: number = 33) {
    this.nowList = nameList.map((name, index) => ({
      name,
      drawStartHeightPercent: 50 + itemHeightPercent * index,
    }));
    this.#slotSize = itemHeightPercent * this.nowList.length;
    this.#itemHeightPercent = itemHeightPercent;
  }

  moveUp(movePercent: number = 1) {
    movePercent = Math.max(movePercent, 0);

    this.nowList.forEach((item) => {
      item.drawStartHeightPercent = this.#move(
        item.drawStartHeightPercent,
        -movePercent,
      );
    });
  }

  moveDown(movePercent: number = 1) {
    movePercent = Math.max(movePercent, 0);
    this.nowList.forEach((item) => {
      item.drawStartHeightPercent = this.#move(
        item.drawStartHeightPercent,
        movePercent,
      );
    });
  }

  #move(position: number, percent: number) {
    const nextPosition = (position + percent + this.#slotSize) % this.#slotSize;
    if (nextPosition >= this.#slotSize - this.#itemHeightPercent)
      return (nextPosition % this.#itemHeightPercent) - this.#itemHeightPercent;
    return nextPosition;
  }
}

class SlotPhaser {
  #accCount = 0;
  #catchingStartCount: number;
  #catchingIndexDiff: number = 0;
  #itemHeightPercent;
  nowPhase: SlotPhase = SPIN_PHASE;

  constructor(
    catchingStartCount: number,
    catchingIndexDiff: number = 0,
    itemHeightPercent: number,
  ) {
    this.#catchingStartCount = catchingStartCount;
    this.#catchingIndexDiff = catchingIndexDiff;
    this.#itemHeightPercent = itemHeightPercent;
  }

  getNextPhase(slotItemList: SlotItem[], loserIndex: number) {
    this.#updateAccTime();

    if (this.nowPhase === FINISH_PHASE) {
      return (this.nowPhase = FINISH_PHASE);
    }

    if (this.#accCount < this.#catchingStartCount) {
      return (this.nowPhase = SPIN_PHASE);
    }

    if (this.nowPhase === SPIN_PHASE) {
      return (this.nowPhase = CATCH_PHASE);
    }
    if (this.nowPhase === CATCH_PHASE) {
      const targetIndex =
        (loserIndex + this.#catchingIndexDiff + slotItemList.length) %
        slotItemList.length;
      const isCaught = slotItemList.some(
        (item, index) =>
          index === targetIndex &&
          this.#checkIsCaught(item.drawStartHeightPercent),
      );
      if (isCaught) return (this.nowPhase = ADJUST_PHASE);
      return (this.nowPhase = CATCH_PHASE);
    }

    if (this.nowPhase === ADJUST_PHASE) {
      const isFinished = slotItemList.some(
        (item, index) =>
          index === loserIndex && item.drawStartHeightPercent === 50,
      );
      if (isFinished) return (this.nowPhase = FINISH_PHASE);
      return (this.nowPhase = ADJUST_PHASE);
    }

    return this.nowPhase;
  }

  #updateAccTime() {
    this.#accCount++;
  }

  #checkIsCaught(percent: number) {
    return (
      50 - this.#itemHeightPercent < percent &&
      percent <= 50 + this.#itemHeightPercent
    );
  }
}

class SlotItemSpeeder {
  slotNowSpeed = 10;
  #speedChangeTryCount = 0;
  #speedFunc = (cnt: number, nowSpeed: number) => {
    if (cnt % 100 === 0 && nowSpeed > 1) {
      return nowSpeed - 1;
    }
    return nowSpeed;
  };
  constructor(
    slotDefaultSpeed = 10,
    speedFunc?: (cnt: number, nowSpeed?: number) => number,
  ) {
    this.slotNowSpeed = slotDefaultSpeed;
    if (speedFunc) this.#speedFunc = speedFunc;
  }

  tryChangeSpeed() {
    this.slotNowSpeed = this.#speedFunc(
      this.#speedChangeTryCount,
      this.slotNowSpeed,
    );
    this.#speedChangeTryCount++;
  }
}

class SlotController {
  #slotItemMover: SlotItemMover;
  #slotPhaser: SlotPhaser;
  #slotItemSpeeder: SlotItemSpeeder;
  #loserIndex: number;

  constructor(
    slotItemMover: SlotItemMover,
    slotPhaser: SlotPhaser,
    slotItemSpeeder: SlotItemSpeeder,
    loserIndex: number,
  ) {
    this.#slotItemMover = slotItemMover;
    this.#slotPhaser = slotPhaser;
    this.#slotItemSpeeder = slotItemSpeeder;
    this.#loserIndex = loserIndex;
  }

  get phase() {
    return this.#slotPhaser.nowPhase;
  }

  getNextSlotItem() {
    this.#moveItem();
    this.#updatePhase();
    return this.#slotItemMover.nowList;
  }

  #moveItem() {
    this.#slotItemSpeeder.tryChangeSpeed();
    switch (this.#slotPhaser.nowPhase) {
      case FINISH_PHASE:
        break;
      case SPIN_PHASE:
        this.#moveDown();
        break;
      case CATCH_PHASE:
        this.#moveDown();
        break;
      case ADJUST_PHASE: {
        const nowSlotItem = this.#slotItemMover.nowList;
        const loser = nowSlotItem.find(
          (_, index) => index === this.#loserIndex,
        );
        if (!loser) {
          this.#moveDown();
          break;
        }
        if (loser.drawStartHeightPercent < 50) {
          this.#moveDown();
          break;
        }
        this.#moveUp();
        break;
      }
      default:
        this.#moveDown();
    }
  }

  #updatePhase() {
    this.#slotPhaser.getNextPhase(
      this.#slotItemMover.nowList,
      this.#loserIndex,
    );
  }

  #moveDown() {
    this.#slotItemMover.moveDown(this.#slotItemSpeeder.slotNowSpeed);
  }

  #moveUp() {
    this.#slotItemMover.moveUp(8);
    const nowSlotItem = this.#slotItemMover.nowList;
    const loser = nowSlotItem.find((_, index) => index === this.#loserIndex);
    if (!loser) return;
    if (loser.drawStartHeightPercent < 50) loser.drawStartHeightPercent = 50;
  }
}

interface drawRouletteProps {
  canvas: HTMLCanvasElement;
  nameList: string[];
  loser?: string;
  minMs?: number;
  startSpeed?: number;
  backgroundColor?: string;
  font?: string;
  stopSpeed?: number;
  fontColor?: string;
  itemPercent?: number;
  widthPx?: number;
  heightPx?: number;
  onEnd?: () => void;
}

export default function drawRoulette(props: drawRouletteProps) {
  const {
    canvas,
    nameList,
    loser,
    minMs = Infinity,
    startSpeed = 10,
    backgroundColor = 'white',
    font = "700 normal 5rem 'Pretendard'",
    stopSpeed = 3,
    fontColor = 'grey',
    itemPercent = 100,
    widthPx,
    heightPx,
    onEnd,
  } = props;
  const ctx = canvas.getContext('2d');
  if (!ctx) return { clearCanvas: () => {} };

  const { gpw, gph } = getCanvasUtil(canvas, widthPx, heightPx);

  const FRAME_SECOND = 20;
  const slotItemMover = new SlotItemMover(
    nameList.length === 1 ? nameList.concat(nameList) : nameList,
    itemPercent,
  );

  const catchingIndexDiff = -1;
  const slotPhaser = new SlotPhaser(
    minMs / FRAME_SECOND,
    catchingIndexDiff,
    nameList.length === 1 ? 2 : nameList.length,
  );

  const moveCount = (cnt: number, speed?: number) => {
    if (!speed) return startSpeed;
    if (
      cnt >= minMs / FRAME_SECOND / 2 &&
      cnt % 10 === 0 &&
      speed > stopSpeed
    ) {
      return speed - 1;
    }
    return speed;
  };
  const slotItemSlower = new SlotItemSpeeder(startSpeed, moveCount);

  const loserIndex = nameList.findIndex((name) => name === loser);
  const slotController = new SlotController(
    slotItemMover,
    slotPhaser,
    slotItemSlower,
    loserIndex,
  );

  ctx.font = font;
  ctx.textAlign = 'center';
  ctx.textBaseline = 'middle';
  const drawBackground = () => {
    ctx.fillStyle = backgroundColor;
    ctx.fillRect(gpw(0), gph(0), gpw(100), gph(100));
  };

  const drawName = () => {
    ctx.fillStyle = fontColor;
    const nextSlotItem = slotController.getNextSlotItem();
    nextSlotItem.forEach(({ name, drawStartHeightPercent }) => {
      if (drawStartHeightPercent > 100 + itemPercent) return;
      ctx.fillText(name, gpw(50), gph(drawStartHeightPercent));
    });
  };

  const intervalId = setInterval(() => {
    drawBackground();
    drawName();
    if (slotController.phase === FINISH_PHASE) {
      clearInterval(intervalId);
      if (onEnd) onEnd();
    }
  }, FRAME_SECOND);

  const clearCanvas = () => clearInterval(intervalId);

  return { clearCanvas };
}
