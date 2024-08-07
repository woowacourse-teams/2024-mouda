import {
  Interpolation,
  SerializedStyles,
  Theme,
  useTheme,
} from '@emotion/react';

import { ReactNode } from 'react';
import { shapes } from '@_components/Button/Button.style';
import { useTheme } from '@emotion/react';

export interface ButtonProps {
  shape: 'circle' | 'bar';
  onClick?: () => void;
  disabled?: boolean;
  primary?: boolean;
  secondary?: boolean;
  reversePrimary?: boolean;
  hasBorder?: boolean;

  font?: SerializedStyles | Interpolation<Theme>;
  children: ReactNode;
}

export default function Button(props: ButtonProps) {
  const { onClick, disabled, children, font } = props;
  const theme = useTheme();
  return (
    <button
      css={[shapes({ ...props, theme }), font]}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
