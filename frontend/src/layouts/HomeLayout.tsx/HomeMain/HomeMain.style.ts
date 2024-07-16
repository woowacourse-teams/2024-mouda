import { css } from '@emotion/react';

export const mainStyle = css`
  padding: 0px 22px;
  display: flex;
  flex-direction: column;
  gap: 38px;
`;

export const navStyle = css`
  display: flex;
  gap: 12px;
`;

export const navItemStyle = (isTurnedOn: boolean) => {
  return css`
    color: ${isTurnedOn ? 'rgba(71, 123, 255, 1)' : '#ededed'};
    font-weight: 600;
    font-size: 18px;
    line-height: 22.88px;
    border-bottom: solid 2px;
  `;
};
