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
  primary: string | SerializedStyles;
  secondary: string | SerializedStyles;
  disabled: string | SerializedStyles;
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
  partialSansKR: SerializedStyles;
}

export interface ColoredTypography {
  h1: (fontColor: string | SerializedStyles) => SerializedStyles;
  h2: (fontColor: string | SerializedStyles) => SerializedStyles;
  h3: (fontColor: string | SerializedStyles) => SerializedStyles;
  h4: (fontColor: string | SerializedStyles) => SerializedStyles;
  h5: (fontColor: string | SerializedStyles) => SerializedStyles;
  s1: (fontColor: string | SerializedStyles) => SerializedStyles;
  s2: (fontColor: string | SerializedStyles) => SerializedStyles;
  b1: (fontColor: string | SerializedStyles) => SerializedStyles;
  b2: (fontColor: string | SerializedStyles) => SerializedStyles;
  b3: (fontColor: string | SerializedStyles) => SerializedStyles;
  b4: (fontColor: string | SerializedStyles) => SerializedStyles;
  c1: (fontColor: string | SerializedStyles) => SerializedStyles;
  c2: (fontColor: string | SerializedStyles) => SerializedStyles;
  c3: (fontColor: string | SerializedStyles) => SerializedStyles;
  label: (fontColor: string | SerializedStyles) => SerializedStyles;
  ButtonFont: (fontColor: string | SerializedStyles) => SerializedStyles;
  Typeface: (fontColor: string | SerializedStyles) => SerializedStyles;
  Giant: (fontColor: string | SerializedStyles) => SerializedStyles;
  Large: (fontColor: string | SerializedStyles) => SerializedStyles;
  Medium: (fontColor: string | SerializedStyles) => SerializedStyles;
  small: (fontColor: string | SerializedStyles) => SerializedStyles;
  Tiny: (fontColor: string | SerializedStyles) => SerializedStyles;
  tag: (fontColor: string | SerializedStyles) => SerializedStyles;
  partialSansKR: (fontColor: string | SerializedStyles) => SerializedStyles;
}
export interface Layout {
  default: SerializedStyles;
}

export interface Theme {
  colorPalette: Colors;
  typography: Typography;
  semantic: Semantic;
  layout: Layout;
  coloredTypography: ColoredTypography;
}
