const getCanvasUtil = (canvas: HTMLCanvasElement) => {
  return {
    /**
     * getPercentToWidth
     * @param percent 1~100
     * @param canvas
     * @returns percentNumber
     */

    gpw: (percent: number) => {
      const width = canvas.width;
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
      const height = canvas.height;
      percent = Math.min(percent, height);

      return (percent / 100) * height;
    },
  };
};

interface SlotItem {
  name: string;
  drawStartHeightPercent: number;
  entireListIndex: number;
}
class SlotItemMaker {
  #itemHeightPercent: number;
  #entireList: string[];
  #numberOfItem: number;
  nowList: SlotItem[];

  constructor(nameList: string[], itemHeightPercent: number = 33) {
    this.#itemHeightPercent = itemHeightPercent;
    this.#entireList = nameList.slice();
    this.#numberOfItem = Math.ceil(100 / itemHeightPercent) + 1;

    this.nowList = [];
    for (let i = 0; i < this.#numberOfItem; i++) {
      const nowName = this.#entireList[i % this.#entireList.length];
      this.nowList.push({
        name: nowName,
        drawStartHeightPercent: itemHeightPercent * i,
        entireListIndex: i,
      });
    }
  }

  updateNowList(movePercent: number = 1) {
    movePercent = Math.max(movePercent, 0);
    let nowStartEntireIndex = this.nowList[0].entireListIndex;
    let nowDrawStartHeightPercent =
      this.nowList[0].drawStartHeightPercent - movePercent;

    while (nowDrawStartHeightPercent < -this.#itemHeightPercent) {
      nowStartEntireIndex = (nowStartEntireIndex + 1) % this.#entireList.length;
      nowDrawStartHeightPercent += this.#itemHeightPercent;
    }

    const nextList = [];
    for (let i = 0; i < this.#numberOfItem; i++) {
      const nowEntireListIndex =
        (nowStartEntireIndex + i) % this.#entireList.length;
      const nowName = this.#entireList[nowEntireListIndex];
      nextList.push({
        name: nowName,
        drawStartHeightPercent:
          nowDrawStartHeightPercent + this.#itemHeightPercent * i,
        entireListIndex: nowEntireListIndex,
      });
    }

    this.nowList = nextList;
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
  fontSize?: number;
  stopSpeed?: number;
  fontColor?: string;
}

export default function drawRoulette(props: drawRouletteProps) {
  const {
    canvas,
    nameList,
    loser,
    minMs = Infinity,
    startSpeed = 10,
    backgroundColor = 'white',
    font = 'Arial',
    fontSize = 20,
    stopSpeed = 3,
    fontColor = 'grey',
  } = props;
  const ctx = canvas.getContext('2d');
  if (!ctx) return;

  // const TOP_PIXEL_PERCENT = 10;
  // const LEFT_PIXEL_PERCENT = 10;
  // const SLOT_WIDTH_PERCENT = 80;
  // const SLOT_HEIGHT_PERCENT = 80;
  const sortedNameList = nameList.slice().sort(() => Math.random() - 0.5);

  const { gpw, gph } = getCanvasUtil(canvas);
  const slotItemMaker = new SlotItemMaker(sortedNameList, 100);
  const FRAME_PER_SECOND = 10;

  let accMs = 0;
  let speed = startSpeed;

  const intervalId = setInterval(() => {
    ctx.fillStyle = backgroundColor;
    ctx.fillRect(gpw(0), gph(0), gpw(100), gph(100));

    ctx.font = `${fontSize}px ${font}`;
    ctx.fillStyle = fontColor;
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    let isCaptured = false;
    slotItemMaker.nowList.forEach(({ name, drawStartHeightPercent }) => {
      ctx.fillText(name, gpw(50), gph(drawStartHeightPercent));
      if (name === loser && drawStartHeightPercent === 50) isCaptured = true;
    });
    if (accMs > minMs && accMs % 100 === 0 && speed > stopSpeed) {
      speed--;
    }

    if (isCaptured && accMs > minMs) {
      clearInterval(intervalId);
    }
    slotItemMaker.updateNowList(speed);
    accMs += FRAME_PER_SECOND;
  }, FRAME_PER_SECOND);
}
