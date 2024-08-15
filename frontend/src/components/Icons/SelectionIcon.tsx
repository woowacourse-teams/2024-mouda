import { useTheme } from '@emotion/react';

interface SelectionIconProps {
  isSelected: boolean;
}

export default function SelectionIcon(props: SelectionIconProps) {
  const { isSelected } = props;
  console.log('isSelected', isSelected);

  const theme = useTheme();

  if (isSelected) {
    return (
      <svg
        width="25"
        height="25"
        viewBox="0 0 25 25"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle
          cx="12.5"
          cy="12.5"
          r="11"
          fill="white"
          stroke="white"
          strokeWidth="3"
        />
        <path
          d="M19 8L13.6031 14.4763C12.6197 15.6563 12.128 16.2464 11.4672 16.2763C10.8063 16.3063 10.2632 15.7632 9.17708 14.6771L7 12.5"
          stroke={theme.semantic.primary}
          strokeWidth="2"
          strokeLinecap="round"
        />
      </svg>
    );
  }

  return (
    <svg
      width="25"
      height="25"
      viewBox="0 0 25 25"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle
        cx="12.5"
        cy="12.5"
        r="11"
        stroke={theme.semantic.disabled}
        strokeWidth="3"
      />
    </svg>
  );
}
