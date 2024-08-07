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

export interface Semantic {
  primary: string;
  secondary: string;
  disabled: string;
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
  tag: SerializedStyles;
}

export interface ColoredTypography {
  h1: (fontColor: string) => SerializedStyles;
  h2: (fontColor: string) => SerializedStyles;
  h3: (fontColor: string) => SerializedStyles;
  h4: (fontColor: string) => SerializedStyles;
  h5: (fontColor: string) => SerializedStyles;
  s1: (fontColor: string) => SerializedStyles;
  s2: (fontColor: string) => SerializedStyles;
  b1: (fontColor: string) => SerializedStyles;
  b2: (fontColor: string) => SerializedStyles;
  b3: (fontColor: string) => SerializedStyles;
  b4: (fontColor: string) => SerializedStyles;
  c1: (fontColor: string) => SerializedStyles;
  c2: (fontColor: string) => SerializedStyles;
  c3: (fontColor: string) => SerializedStyles;
  label: (fontColor: string) => SerializedStyles;
  ButtonFont: (fontColor: string) => SerializedStyles;
  Typeface: (fontColor: string) => SerializedStyles;
  Giant: (fontColor: string) => SerializedStyles;
  Large: (fontColor: string) => SerializedStyles;
  Medium: (fontColor: string) => SerializedStyles;
  small: (fontColor: string) => SerializedStyles;
  Tiny: (fontColor: string) => SerializedStyles;
  tag: (fontColor: string) => SerializedStyles;
}
export interface Layout {
  default: SerializedStyles;
}

export interface Theme {
  colorPalette: Colors;
  typography: Typography;
  semantic: Semantic;
  layout: Layout;
}
