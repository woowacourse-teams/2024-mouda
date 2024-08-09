import { Theme, css } from '@emotion/react';

import { ButtonProps } from './Button';

const defaultFont = css`
  font-size: 1.6rem;
  font-weight: 700;
  font-style: normal;
  line-height: normal;
  color: #fff;
  letter-spacing: -0.032rem;
`;

type themeStyleArgs = Pick<
  ButtonProps,
  'disabled' | 'primary' | 'secondary' | 'reversePrimary'
> & { theme: Theme };

const themeStyle = ({
  theme,
  disabled,
  primary,
  secondary,
  reversePrimary,
}: themeStyleArgs) => {
  if (disabled) {
    return css`
      pointer-events: none;
      color: ${theme.colorPalette.white[100]};
      background-color: ${theme.semantic.disabled};
    `;
  }
  if (primary) {
    return css`
      color: ${theme.colorPalette.white[100]};
      background-color: ${theme.semantic.primary};

      &:active {
        background-color: ${theme.colorPalette.orange[900]};
      }
    `;
  }
  if (secondary) {
    return css`
      color: ${theme.colorPalette.white[100]};
      background-color: ${theme.semantic.secondary};

      &:active {
        background-color: ${theme.colorPalette.yellow[300]};
      }
    `;
  }

  if (reversePrimary) {
    return css`
      color: ${theme.semantic.primary};
      background-color: ${theme.colorPalette.white[100]};
      border: solid 1px ${theme.semantic.primary};

      &:active {
        background-color: ${theme.colorPalette.grey[300]};
      }
    `;
  }

  // default:primary
  return css`
    color: ${theme.colorPalette.white};
    background-color: ${theme.semantic.primary};

    &:active {
      background-color: ${theme.colorPalette.orange[500]};
    }
  `;
};

type shapeStyleArgs = Pick<ButtonProps, 'shape'>;

const shapeStyle = ({ shape }: shapeStyleArgs) => {
  if (shape === 'circle') {
    return css`
      border: none;
      border-radius: 50%;
      box-shadow: 0 0 3px #444;
    `;
  }
  if (shape === 'bar') {
    return css`
      display: flex;
      gap: 1rem;
      align-items: center;
      justify-content: center;

      width: 100%;
      height: 6.4rem;
      padding: 1.6rem 5.9rem;

      border: none;
      border-radius: 3rem;
    `;
  }
};

type ShapeArgs = ButtonProps & { theme: Theme };
export const shapes = ({
  theme,
  shape,

  disabled,
  primary,
  reversePrimary,

  secondary,
}: ShapeArgs) => {
  const defaultStyle = css`
    user-select: none;
    flex-shrink: 0;
  `;
  return [
    defaultStyle,
    defaultFont,
    shapeStyle({ shape }),
    themeStyle({ theme, disabled, primary, secondary, reversePrimary }),
  ];
};
