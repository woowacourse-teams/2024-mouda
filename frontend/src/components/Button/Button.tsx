import { ReactNode } from 'react';
import { shapes } from '@_components/Button/Button.style';
import { useTheme } from '@emotion/react';

interface ButtonProps {
  shape: 'circle' | 'bar';
  onClick?: () => void;
  disabled: boolean;
  children: ReactNode;
}

export default function Button(props: ButtonProps) {
  const { shape, onClick, disabled, children } = props;
  const theme = useTheme();
  return (
    <button
      css={shapes(shape, disabled, theme)}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
