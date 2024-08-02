import { useTheme } from '@emotion/react';

export default function PlusIcon() {
  const theme = useTheme();

  return (
    <svg
      width="46"
      height="46"
      viewBox="0 0 46 46"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M23 9.58334V36.4167M9.58333 23H36.4167"
        stroke={theme.semantic.primary}
        strokeWidth="5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}
