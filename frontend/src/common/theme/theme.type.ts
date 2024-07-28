import { SerializedStyles } from '@emotion/react';

export interface Colors {
  black: Record<number, string>;
  white: Record<number, string>;
  grey: Record<number, string>;
  green: Record<number, string>;
  orange: Record<number, string>;
  yellow: Record<number, string>;
  red: Record<number, string>;
}

export interface Typography {
  h1: SerializedStyles;
  h2: SerializedStyles;
  h3: SerializedStyles;
  h4: SerializedStyles;
  h5: SerializedStyles;
  s1: SerializedStyles;
  s2: SerializedStyles;
  b1: SerializedStyles;
  b2: SerializedStyles;
  b3: SerializedStyles;
  b4: SerializedStyles;
  c1: SerializedStyles;
  c2: SerializedStyles;
  c3: SerializedStyles;
  label: SerializedStyles;
  ButtonFont: SerializedStyles;
  Typeface: SerializedStyles;
  Giant: SerializedStyles;
  Large: SerializedStyles;
  Medium: SerializedStyles;
  small: SerializedStyles;
  Tiny: SerializedStyles;
}

export interface Theme {
  colors: Colors;
  typography: Typography;
}
