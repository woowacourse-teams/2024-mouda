import { css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const titleStyle = css`
  font-size: 26px;
  font-weight: 700;
`;

export const contentStyle = css`
  display: flex;
  align-items: center;
  gap: 6px;
`;

export const contentKeyStyle = css`
  background: rgba(221, 231, 255, 1);
  border-radius: 10px;
  padding: 2px 6px;
  color: rgba(71, 123, 255, 1);
  font-size: 12px;
  font-weight: 600;
`;

export const contentValueStyle = css`
  font-size: 14px;
  font-weight: 500;
`;
