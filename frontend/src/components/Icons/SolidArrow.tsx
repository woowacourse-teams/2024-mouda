import { SVGProps } from 'react';

interface SolidArrowProps extends SVGProps<SVGSVGElement> {
  direction: 'up' | 'down' | 'left' | 'right';
  color?: string;
}

const directionMapper: Record<SolidArrowProps['direction'], number> = {
  up: 90,
  right: 180,
  down: 270,
  left: 0,
};

export default function SolidArrow(props: SolidArrowProps) {
  const { direction, color, ...otherProps } = props;

  return (
    <svg
      width="27"
      height="27"
      viewBox="0 0 27 27"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      transform={`rotate(${directionMapper[direction]})`}
      {...otherProps}
    >
      <path
        d="M16.8446 23.5825L6.73785 12.8812L16.8446 3.36893"
        stroke={color || '#767676'}
        strokeWidth="2.24595"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}
