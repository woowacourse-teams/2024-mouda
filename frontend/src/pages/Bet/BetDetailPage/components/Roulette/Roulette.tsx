import { Participant } from '@_types/index';
import { css, keyframes, useTheme } from '@emotion/react';

interface RouletteProps {
  participants: Participant[];
  isFast: boolean; // prop 추가
}

export default function Roulette({ participants, isFast }: RouletteProps) {
  const theme = useTheme();
  const colors = [
    theme.colorPalette.yellow[100],
    theme.colorPalette.orange[100],
    theme.colorPalette.red[100],
    theme.colorPalette.yellow[300],
    theme.colorPalette.green[300],
    theme.colorPalette.orange[300],
    theme.colorPalette.green[100],
    theme.colorPalette.red[300],
    theme.colorPalette.orange[500],
    theme.colorPalette.red[500],
  ];

  const totalParticipants = participants.length;
  const segmentAngle = 360 / totalParticipants;
  const radius = 100;
  const center = 120;

  const calculateCoordinates = (angle: number, radiusScale = 1) => {
    const radians = (angle * Math.PI) / 180;
    const adjustedRadius = radius * radiusScale;
    return {
      x: center + adjustedRadius * Math.cos(radians),
      y: center + adjustedRadius * Math.sin(radians),
    };
  };

  const segments = participants.map((participant, index) => {
    const startAngle = index * segmentAngle;
    const endAngle = startAngle + segmentAngle;
    const { x: startX, y: startY } = calculateCoordinates(startAngle);
    const { x: endX, y: endY } = calculateCoordinates(endAngle);
    const largeArcFlag = segmentAngle > 180 ? 1 : 0;

    // 텍스트를 조금 더 바깥으로 배치하기 위해 0.7을 바꿔줄 수 있음
    const middleAngle = startAngle + segmentAngle / 2;
    const textCoordinates = calculateCoordinates(middleAngle, 0.65);

    return (
      <g key={participant.id}>
        <path
          d={`M${center},${center} L${startX},${startY} A${radius},${radius} 0 ${largeArcFlag} 1 ${endX},${endY} Z`}
          fill={colors[index]}
          stroke="#FFFFFF"
          strokeWidth="2"
        />
        <text
          x={textCoordinates.x}
          y={textCoordinates.y}
          textAnchor="middle"
          fontSize="18"
          fontWeight="bold"
          fill="#000"
          alignmentBaseline="middle"
        >
          {participant.nickname}
        </text>
      </g>
    );
  });

  return (
    <div css={rouletteContainerStyle}>
      <div css={rouletteStyle(isFast)}>
        <svg viewBox="0 0 240 240">
          {segments}
          <circle
            cx={center}
            cy={center}
            r={20}
            fill="#FFFFFF"
            stroke="#000"
            strokeWidth="2"
          />
        </svg>
      </div>
    </div>
  );
}

// 일정한 속도로 무한 회전하는 기본 애니메이션 설정
const infiniteRotation = keyframes`
  100% {
    transform: rotate(360deg);
  }
`;

// 점진적 가속 애니메이션 설정
const gradualAcceleration = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(1080deg); /* 빠르게 추가로 3회전 */
  }
`;

const rouletteContainerStyle = css`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const rouletteStyle = (isFast: boolean) => css`
  transform-origin: 50% 50%;

  width: 250px;
  height: 250px;

  border-radius: 50%;
  box-shadow: 0 4px 10px rgb(0 0 0 / 20%);

  ${isFast
    ? css`
        animation: ${gradualAcceleration} 2s ease-in forwards;
      `
    : css`
        animation: ${infiniteRotation} 10s linear infinite;
      `}
`;
