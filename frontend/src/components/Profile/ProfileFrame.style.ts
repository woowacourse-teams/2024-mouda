import { css } from '@emotion/react';

type Size = string | number | undefined;

export const profileFrame = (width: Size, height: Size) => {
  return css`
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;

    width: ${width};
    height: ${height};

    border: 0.5rem solid orange;
    border-radius: 300rem;
  `;
};

export const profileImage = () => {
  return css`
    width: 100%;
    height: 100%;
    object-fit: cover;
  `;
};
