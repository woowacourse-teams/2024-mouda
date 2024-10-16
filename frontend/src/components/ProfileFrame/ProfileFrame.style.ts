import { css } from '@emotion/react';
import EmptyProfile from '@_common/assets/empty_profile.svg?url';
type Size = number;

export const profileBox = () => {
  return css`
    position: relative;
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

export const profileFrame = (width: Size, height: Size, borderWidth: Size) => {
  return css`
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;

    width: ${width}rem;
    height: ${height}rem;

    border: ${borderWidth}rem solid orange;
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
    background-image: url(${EmptyProfile});
    background-position: center;
    background-size: cover;
  `;
};
