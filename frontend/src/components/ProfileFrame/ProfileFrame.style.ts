import { css, Theme } from '@emotion/react';
import EmptyProfile from '@_common/assets/default_profile.svg?url';
type Size = number;

export const profileBox = () => {
  return css`
    position: relative;
    z-index: -1;
  `;
};

export const profileCrown = (width: Size) => {
  return css`
    position: absolute;
    top: -14px;
    left: 50%;
    transform: translateX(-50%);

    width: ${3 * (width / 8)}rem;
  `;
};

export const profileFrame = ({
  width,
  height,
  borderWidth,
  theme,
}: {
  width: Size;
  height: Size;
  borderWidth: Size;
  theme: Theme;
}) => {
  return css`
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;

    width: ${width}rem;
    height: ${height}rem;

    background: ${theme.colorPalette.white[100]};
    border: ${borderWidth}rem solid ${theme.colorPalette.orange[200]};
    border-radius: 300rem;
  `;
};

export const profileImage = (
  props: { isLoaded?: boolean } = { isLoaded: true },
) => {
  return css`
    ${!props.isLoaded && 'display: none;'};
    width: 100%;
    height: 100%;

    object-fit: cover;
    background-image: url(${props.isLoaded ? '' : EmptyProfile});
    background-position: center;
    background-size: cover;
  `;
};
