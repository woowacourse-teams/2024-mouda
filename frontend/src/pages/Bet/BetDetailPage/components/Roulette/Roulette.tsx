import { Participant } from '@_types/index';
import { css, keyframes, useTheme } from '@emotion/react';

interface RouletteProps {
  participants: Participant[];
  isFast: boolean; // true일 때 빠르게 회전
}

export default function Roulette({ participants, isFast }: RouletteProps) {
  const theme = useTheme();

  // 색상 배열: 색상을 순차적으로 각 섹터에 적용
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

  // 각 섹터의 각도를 계산하여 원을 나누는 데 사용
  const totalParticipants = participants.length;
  const segmentAngle = 360 / totalParticipants;
  const radius = 100; // 원의 반지름
  const center = 120; // SVG 뷰박스의 중심 좌표

  // 주어진 각도에서 원의 좌표를 계산하는 함수
  // radiusScale로 반지름의 비율을 조정해 텍스트 위치를 설정
  const calculateCoordinates = (angle: number, radiusScale = 1) => {
    const radians = (angle * Math.PI) / 180;
    const adjustedRadius = radius * radiusScale;
    return {
      x: center + adjustedRadius * Math.cos(radians),
      y: center + adjustedRadius * Math.sin(radians),
    };
  };

  // 각 섹터 생성: 섹터별 경로 및 텍스트 위치를 설정
  const segments = participants.map((participant, index) => {
    const startAngle = index * segmentAngle; // 시작 각도
    const endAngle = startAngle + segmentAngle; // 끝 각도

    // 섹터의 시작 및 끝 좌표 계산
    const { x: startX, y: startY } = calculateCoordinates(startAngle);
    const { x: endX, y: endY } = calculateCoordinates(endAngle);
    const largeArcFlag = segmentAngle > 180 ? 1 : 0; // 호의 크기 제어

    // 텍스트 좌표 계산: 0.65 비율을 사용해 텍스트를 조금 더 바깥으로 이동
    const middleAngle = startAngle + segmentAngle / 2;
    const textCoordinates = calculateCoordinates(middleAngle, 0.65);

    return (
      <g key={participant.id}>
        {/* 섹터 패스 생성 */}
        <path
          d={`M${center},${center} L${startX},${startY} A${radius},${radius} 0 ${largeArcFlag} 1 ${endX},${endY} Z`}
          fill={colors[index]} // 색상 배열에서 인덱스에 맞는 색상 할당
          stroke="#FFFFFF" // 섹터 간 구분을 위한 흰색 테두리
          strokeWidth="2"
        />
        {/* 섹터 중앙에 텍스트 배치 */}
        <text
          x={textCoordinates.x}
          y={textCoordinates.y}
          textAnchor="middle" // 텍스트 중앙 정렬
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
      {/* 조건에 따라 다른 애니메이션 적용 */}
      <div css={rouletteStyle(isFast)}>
        <svg viewBox="0 0 240 240">
          {segments}
          {/* 중앙 원: 중심 축 강조 */}
          <circle
            cx={center}
            cy={center}
            r={20} // 중앙 원 반지름
            fill="#FFFFFF"
            stroke="#000"
            strokeWidth="2"
          />
        </svg>
      </div>
    </div>
  );
}

// 일정한 속도로 무한 회전하는 기본 애니메이션
const infiniteRotation = keyframes`
  100% {
    transform: rotate(360deg);
  }
`;

// 점진적 가속 애니메이션: 'isFast'가 true일 때 적용
// 1080도 회전하여 빠르게 3회전
const gradualAcceleration = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(1080deg);
  }
`;

const rouletteContainerStyle = css`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

// 'isFast' prop에 따라 다른 애니메이션 적용
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
