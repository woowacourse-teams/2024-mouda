import { ReactNode } from 'react';

export default interface ButtonProps {
  shape: 'circle' | 'bar';
  onClick: () => void;
  disabled: boolean;
  children: ReactNode;
}
