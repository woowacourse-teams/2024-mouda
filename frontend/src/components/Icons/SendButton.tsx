import { useTheme } from '@emotion/react';

interface SendButtonProps {
  disabled: boolean;
}

export default function SendButton(props: SendButtonProps) {
  const { disabled } = props;
  const theme = useTheme();

  return (
    <svg
      width="19"
      height="18"
      viewBox="0 0 19 18"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M0.402208 3.67292C0.142208 1.33892 2.54521 -0.375085 4.66821 0.630915L16.6122 6.28892C18.9002 7.37191 18.9002 10.6279 16.6122 11.7109L4.66821 17.3699C2.54521 18.3759 0.143208 16.6619 0.402208 14.3279L0.882208 9.99992H9.00021C9.26542 9.99992 9.51978 9.89456 9.70731 9.70702C9.89485 9.51949 10.0002 9.26513 10.0002 8.99992C10.0002 8.7347 9.89485 8.48035 9.70731 8.29281C9.51978 8.10527 9.26542 7.99992 9.00021 7.99992H0.883208L0.402208 3.67292Z"
        fill={
          disabled
            ? theme.colorPalette.grey[400]
            : theme.colorPalette.orange[300]
        }
      />
    </svg>
  );
}
