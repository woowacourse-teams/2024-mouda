import { SVGAttributes } from 'react';

interface HamburgerProps extends SVGAttributes<HTMLOrSVGElement> {
  color?: string;
  strokeWidth?: number;
  width?: number;
  height?: number;
}

export default function Hamburger(props: HamburgerProps) {
  const { color, strokeWidth, width, height } = props;

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={width || '25'}
      height={height || '18'}
      viewBox="0 0 25 18"
      fill="none"
      {...props}
    >
      <path
        d="M1.47559 1H23.4756M1.47559 9H23.4756M1.47559 17H23.4756"
        stroke={color || 'black'}
        strokeWidth={strokeWidth || '2'}
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}
