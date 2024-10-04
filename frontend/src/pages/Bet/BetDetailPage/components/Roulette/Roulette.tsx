import { Participant } from '@_types/index';
import { css } from '@emotion/react';

interface RouletteProps {
  participants: Participant[];
}

export default function Roulette({ participants }: RouletteProps) {
  const totalParticipants = participants.length;
  const segmentAngle = 360 / totalParticipants;
  const radius = 100; // 원의 반지름
  const center = 120; // SVG 뷰박스의 중심

  // 주어진 각도에서 원의 좌표를 계산하는 함수 (radiusScale: 반지름 비율)
  const calculateCoordinates = (angle: number, radiusScale = 1) => {
    const radians = (angle * Math.PI) / 180;
    const adjustedRadius = radius * radiusScale;
    return {
      x: center + adjustedRadius * Math.cos(radians),
      y: center + adjustedRadius * Math.sin(radians),
    };
  };

  const segments = participants.map((participant, index) => {
    console.log(participant, index);
    const startAngle = index * segmentAngle;
    const endAngle = startAngle + segmentAngle;

    const { x: startX, y: startY } = calculateCoordinates(startAngle);
    const { x: endX, y: endY } = calculateCoordinates(endAngle);

    const largeArcFlag = segmentAngle > 180 ? 1 : 0;

    // 텍스트 배치 위치 계산 (섹터의 중앙으로, 반지름의 50% 위치)
    const middleAngle = startAngle + segmentAngle / 2;
    const textCoordinates = calculateCoordinates(middleAngle, 0.5); // 반지름의 50% 지점으로 설정

    return (
      <g key={participant.id}>
        <path
          d={`M${center},${center} L${startX},${startY} A${radius},${radius} 0 ${largeArcFlag} 1 ${endX},${endY} Z`}
          fill={
            index === 0
              ? '#FF0000'
              : index === 1
                ? '#FFD700'
                : index === 2
                  ? '#FFA500'
                  : index === 3
                    ? '#FF69B4'
                    : '#00FF00'
          }
        />
        <text
          x={textCoordinates.x} // 텍스트 X 좌표를 조정된 위치로
          y={textCoordinates.y} // 텍스트 Y 좌표를 조정된 위치로
          textAnchor="middle"
          fontSize="20"
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
      <svg viewBox="0 0 240 240" css={rouletteStyle}>
        {segments}
      </svg>
    </div>
  );
}

const rouletteContainerStyle = css`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const rouletteStyle = css`
  width: 250px;
  height: 250px;
`;
