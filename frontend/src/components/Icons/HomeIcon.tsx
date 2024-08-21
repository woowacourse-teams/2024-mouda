import { useTheme } from '@emotion/react';

interface HomeIconProps {
  isActive: boolean;
}

export default function HomeIcon(props: HomeIconProps) {
  const { isActive } = props;

  const theme = useTheme();

  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M19.444 7.47307L17.774 17.4731C17.6613 18.1687 17.3072 18.8024 16.774 19.2631C16.2058 19.7161 15.5007 19.9628 14.774 19.9631H5.16402C4.46134 19.9569 3.78312 19.7043 3.24766 19.2492C2.71219 18.7941 2.35346 18.1656 2.23402 17.4731L0.564023 7.47307C0.473584 6.8961 0.553519 6.30527 0.794023 5.77307C1.03445 5.24361 1.42356 4.79544 1.91402 4.48307L8.39402 0.48307C8.86746 0.191034 9.41276 0.036377 9.96902 0.036377C10.5253 0.036377 11.0706 0.191034 11.544 0.48307L18.014 4.48307C18.521 4.78307 18.925 5.23007 19.174 5.76307C19.4301 6.29415 19.524 6.88892 19.444 7.47307Z"
        fill={
          isActive ? theme.colorPalette.grey[500] : theme.colorPalette.grey[300]
        }
      />
    </svg>
  );
}
