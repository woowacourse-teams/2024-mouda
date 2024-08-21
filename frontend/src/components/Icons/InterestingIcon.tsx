import { useTheme } from '@emotion/react';

interface InterestingIconProps {
  isActive: boolean;
}

export default function InterestingIcon(props: InterestingIconProps) {
  const { isActive } = props;

  const theme = useTheme();

  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 19 21"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M8.2 2C8.2 0.895 8.896 0 10 0C11.104 0 11.8 0.895 11.8 2L12 10C12 9.636 12.5 4.34 12.5 4C12.5 3 13.095 2 14.2 2C15.305 2 16 2.895 16 4V11.268C16.083 11.22 16.3 7.422 16.3 7C16.3 6 16.563 5 17.5 5C18.438 5 19 5.895 19 7V13C19 15.1217 18.1571 17.1566 16.6569 18.6569C15.1566 20.1571 13.1217 21 11 21H10.326C8.84041 21 7.38417 20.5862 6.12045 19.8052C4.85672 19.0242 3.83543 17.9067 3.171 16.578L0.329001 10.894C-0.0349991 10.166 0.245001 9.226 1.049 8.87C1.472 8.683 1.946 8.578 2.392 8.72C3.5 9.073 3.336 9.58 4 10.21V4C4 2.895 4.695 2 5.8 2C6.8 2 7.409 3.315 7.5 4C7.625 4.938 8 9.634 8 9.998L8.2 2Z"
        fill={isActive ? theme.semantic.primary : theme.semantic.disabled}
      />
    </svg>
  );
}
