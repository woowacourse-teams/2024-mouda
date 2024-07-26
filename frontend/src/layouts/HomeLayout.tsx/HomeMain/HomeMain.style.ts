import { css } from '@emotion/react';

export const mainStyle = css`
  display: flex;
  flex-direction: column;
  gap: 38px;
  padding: 0 22px;
`;

export const navStyle = css`
  display: flex;
  gap: 12px;
`;

// TODO: 수야 때리기 && 미사용 네비게이션 하단 바 없애기
export const navItemStyle = (isTurnedOn: boolean) => {
  return css`
    font-size: 18px;
    font-weight: 600;
    line-height: 22.88px;
    color: ${isTurnedOn ? 'rgba(71, 123, 255, 1)' : '#868e96'};

    border-bottom: solid 2px;
  `;
};
