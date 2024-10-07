import { useTheme } from '@emotion/react';

interface ScissorsIconProps {
  isActive: boolean;
}

export default function ScissorsIcon(props: ScissorsIconProps) {
  const { isActive } = props;

  const theme = useTheme();

  return (
    <svg
      width="25"
      height="22"
      viewBox="0 0 25 22"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M10.625 19.625C10.625 18.5895 11.4645 17.75 12.5 17.75V17.375H11C9.96448 17.375 9.125 16.5356 9.125 15.5C9.125 14.4645 9.96448 13.625 11 13.625H12.5V13.25H2.75C1.50734 13.25 0.5 12.2427 0.5 11C0.5 9.75737 1.50734 8.75003 2.75 8.75003H12.5V8.11384L4.1773 4.84478C3.0207 4.39037 2.45145 3.08439 2.90581 1.92784C3.36017 0.771296 4.66616 0.202046 5.82275 0.656405L14.745 4.16101L15.9109 2.70367C16.4877 1.98269 17.5036 1.7889 18.3053 2.24706L23.5553 5.24706C23.8423 5.41107 24.0808 5.64803 24.2467 5.93392C24.4126 6.21982 24.5 6.54449 24.5 6.87503V18.125C24.5 18.9951 23.9014 19.7509 23.0545 19.9502L16.6795 21.4502C16.5387 21.4833 16.3946 21.5 16.25 21.5H12.5C11.4645 21.5 10.625 20.6606 10.625 19.625Z"
        fill={
          isActive ? theme.colorPalette.grey[500] : theme.colorPalette.grey[300]
        }
      />
    </svg>
  );
}
