import { useTheme } from '@emotion/react';

interface MyPageIconProps {
  isActive: boolean;
}

export default function MyPageIcon(props: MyPageIconProps) {
  const { isActive } = props;

  const theme = useTheme();

  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 20 19"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M19.3997 18.6251C19.3338 18.7391 19.2391 18.8338 19.1251 18.8997C19.011 18.9655 18.8816 19.0001 18.75 19.0001H0.749964C0.618376 19 0.48914 18.9652 0.375233 18.8993C0.261326 18.8335 0.166756 18.7388 0.101021 18.6248C0.0352859 18.5108 0.000698664 18.3815 0.000732447 18.2499C0.000766229 18.1183 0.0354198 17.9891 0.101214 17.8751C1.52903 15.4067 3.72934 13.6367 6.29715 12.7976C5.027 12.0415 4.04016 10.8893 3.4882 9.51804C2.93624 8.14678 2.84967 6.63224 3.24178 5.20701C3.6339 3.78178 4.48301 2.52467 5.65874 1.62873C6.83446 0.732786 8.27178 0.247559 9.74996 0.247559C11.2281 0.247559 12.6655 0.732786 13.8412 1.62873C15.0169 2.52467 15.866 3.78178 16.2581 5.20701C16.6503 6.63224 16.5637 8.14678 16.0117 9.51804C15.4598 10.8893 14.4729 12.0415 13.2028 12.7976C15.7706 13.6367 17.9709 15.4067 19.3987 17.8751C19.4647 17.989 19.4995 18.1183 19.4997 18.25C19.4998 18.3816 19.4653 18.511 19.3997 18.6251Z"
        fill={
          isActive ? theme.colorPalette.grey[500] : theme.colorPalette.grey[300]
        }
      />
    </svg>
  );
}