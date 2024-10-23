import * as S from './Roulette.styles';

import { HTMLAttributes, useEffect, useRef } from 'react';

import drawRoulette from './DrawRoulette';

interface RouletteProps extends HTMLAttributes<HTMLCanvasElement> {
  nameList: string[];
  loser?: string;
  minMs?: number;
  startSpeed?: number;
  backgroundColor?: string;
  font?: string;
  fontSize?: number;
  stopSpeed?: number;
  fontColor?: string;
  itemPercent?: number;
  onEnd?: () => void;
}

export default function Roulette(props: RouletteProps) {
  const {
    nameList,
    loser,
    minMs = Infinity,
    startSpeed = 500,
    backgroundColor = '#D2D5DB',
    font = "500 normal 5rem 'bitbit'",
    fontSize = 32,
    stopSpeed = 3,
    fontColor = '#6D717F',
    itemPercent = 100,
    onEnd,
    ...otherProps
  } = props;
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const isEnded = useRef(false);

  useEffect(() => {
    if (!canvasRef.current) return;
    if (isEnded.current) return;
    const { clearCanvas } = drawRoulette({
      canvas: canvasRef.current,
      nameList,
      loser,
      minMs,
      startSpeed,
      backgroundColor,
      font,
      stopSpeed,
      fontColor,
      itemPercent,
      onEnd: () => {
        onEnd && onEnd();
        isEnded.current = true;
      },
    });

    return clearCanvas;
  }, [
    nameList,
    loser,
    minMs,
    startSpeed,
    backgroundColor,
    font,
    fontSize,
    stopSpeed,
    fontColor,
    itemPercent,
    onEnd,
  ]);

  return <canvas ref={canvasRef} css={S.canvas} {...otherProps} />;
}
