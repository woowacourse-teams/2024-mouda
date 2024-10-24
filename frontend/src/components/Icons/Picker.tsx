import { useTheme } from '@emotion/react';

interface PickerProps {
  color?: string;
}

export default function Picker(props: PickerProps) {
  const { color } = props;
  const theme = useTheme();

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="22"
      height="25"
      viewBox="0 0 22 25"
      fill="none"
    >
      <path
        d="M20.7245 15.4496C21.5366 13.9535 21.9598 12.2836 21.9564 10.5883C21.9564 4.86852 17.2328 0.231934 11.4057 0.231934C5.57861 0.231934 0.854997 4.86852 0.854997 10.5883C0.850618 13.0313 1.73042 15.3965 3.33752 17.262L3.34993 17.2772L3.3611 17.2894H3.33752L9.59843 23.8139C9.83052 24.0557 10.1105 24.2484 10.4213 24.3801C10.732 24.5118 11.0669 24.5798 11.4054 24.5798C11.7439 24.5798 12.0788 24.5118 12.3895 24.3801C12.7002 24.2484 12.9803 24.0557 13.2124 23.8139L19.4739 17.2894H19.4503L19.4602 17.2778L19.4615 17.2766C19.5062 17.2242 19.5506 17.1714 19.5949 17.1182C20.0255 16.599 20.4038 16.0406 20.7245 15.4496ZM11.4088 14.5474C10.4212 14.5474 9.47404 14.1623 8.7757 13.4769C8.07735 12.7914 7.68503 11.8617 7.68503 10.8923C7.68503 9.92284 8.07735 8.99313 8.7757 8.30765C9.47404 7.62217 10.4212 7.23708 11.4088 7.23708C12.3964 7.23708 13.3436 7.62217 14.0419 8.30765C14.7403 8.99313 15.1326 9.92284 15.1326 10.8923C15.1326 11.8617 14.7403 12.7914 14.0419 13.4769C13.3436 14.1623 12.3964 14.5474 11.4088 14.5474Z"
        fill={color || theme.semantic.primary}
      />
    </svg>
  );
}
