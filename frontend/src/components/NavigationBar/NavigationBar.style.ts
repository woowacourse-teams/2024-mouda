import { css } from '@emotion/react';

export const navigationBarContainer = css`
  display: flex;
  justify-content: space-between;

  width: 100%;
  height: 90px;

  box-shadow: 0 -2px 10px rgb(0 0 0 / 10%);
`;

export const navigationBarList = css`
  display: flex;
  justify-content: space-between;

  width: 100%;
  margin: 0;
  padding: 0;

  list-style: none;
`;

export const navigationBarItem = css`
  display: flex;
  flex: 1; /* 각 아이템이 동일한 너비를 가지도록 설정 */
  flex-direction: column;
  gap: 4px;
  align-items: center;
  justify-content: center;

  color: #6d717f;

  :hover {
    color: #000;
  }
`;
