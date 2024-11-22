import { HTMLAttributes, ReactNode } from 'react';
import {
  Interpolation,
  SerializedStyles,
  Theme,
  useTheme,
} from '@emotion/react';

import { shapes } from '@_components/Button/Button.style';

export interface ButtonProps extends HTMLAttributes<HTMLButtonElement> {
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
  const { onClick, disabled, children, font, ...restProps } = props;
  const theme = useTheme();
  return (
    <button
      css={[shapes({ ...props, theme }), font]}
      onClick={onClick}
      disabled={disabled}
      {...restProps}
    >
      {children}
    </button>
  );
}
