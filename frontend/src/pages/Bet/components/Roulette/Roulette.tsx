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
}

export default function Roulette(props: RouletteProps) {
  const {
    nameList,
    loser,
    minMs = Infinity,
    startSpeed = 500,
    backgroundColor = 'grey',
    font = "700 normal 5rem 'Pretendard'",
    fontSize = 32,
    stopSpeed = 3,
    fontColor = 'black',
    itemPercent = 100,
    ...otherProps
  } = props;
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    if (!canvasRef.current) return;
    drawRoulette({
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
    });
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
  ]);

  return <canvas ref={canvasRef} {...otherProps} />;
}
