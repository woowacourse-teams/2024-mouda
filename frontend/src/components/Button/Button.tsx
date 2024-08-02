import { ReactNode } from 'react';
import { shapes } from '@_components/Button/Button.style';

interface ButtonProps {
  shape: 'circle' | 'bar';
  onClick?: () => void;
  disabled: boolean;
  children: ReactNode;
}

export default function Button(props: ButtonProps) {
  const { shape, onClick, disabled, children } = props;
  return (
    <button css={shapes(shape, disabled)} onClick={onClick} disabled={disabled}>
      {children}
    </button>
  );
}
