import { useEffect, useRef } from 'react';

import drawRoulette from './DrawRoulette';

interface RouletteProps {
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

export default function Roulette(props: RouletteProps) {
  const {
    nameList,
    loser,
    minMs = Infinity,
    startSpeed = 500,
    backgroundColor = 'grey',
    font = 'DOSIyagiBoldface',
    fontSize = 32,
    stopSpeed = 3,
    fontColor = 'black',
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
      fontSize,
      stopSpeed,
      fontColor,
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
  ]);

  return <canvas ref={canvasRef} />;
}
