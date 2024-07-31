import { css } from '@emotion/react';

type Size = number;

export const profileBox = () => {
  return css`
    width: fit-content;
  `;
};

export const profileFrame = (width: Size, height: Size) => {
  return css`
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;

    width: ${width}rem;
    height: ${height}rem;

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

// 크라운 이미지를 가운데 정렬하기 위한 css
export const profileCrown = (width: Size) => {
  return css`
    position: relative;
    top: 1rem;
    left: ${width / 2 - (3 * (width / 8)) / 2}rem;
    width: ${3 * (width / 8)}rem;
  `;
};
