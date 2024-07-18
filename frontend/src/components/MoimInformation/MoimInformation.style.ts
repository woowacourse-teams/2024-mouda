import { css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 14px;
`;

export const titleStyle = css`
  font-size: 16px;
  font-weight: 700;
`;

export const cardStyle = css`
  background-color: #f0f4ff;
  border-radius: 18px;
  padding: 16px 24px;
  color: #333;
`;

export const rowStyle = css`
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-top: 1px solid #e0e0e0;
  color: #666;

  &:first-of-type {
    border-top: none;
    padding-top: 0;
  }

  &:last-of-type {
    border-bottom: none;
    padding-bottom: 0;
  }
`;
