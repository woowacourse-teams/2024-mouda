import { ReactNode } from 'react';

export default interface ButtonProps {
  shape: 'circle' | 'bar';
  onClick: () => void;
  children: ReactNode;
}
