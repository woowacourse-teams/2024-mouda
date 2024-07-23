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
  padding: 16px 24px;
  color: #333;
  background-color: #f0f4ff;
  border-radius: 18px;
`;

export const rowStyle = css`
  display: flex;
  justify-content: space-between;

  padding: 10px 0;

  color: #666;

  border-top: 1px solid #e0e0e0;

  &:first-of-type {
    padding-top: 0;
    border-top: none;
  }

  &:last-of-type {
    padding-bottom: 0;
    border-bottom: none;
  }
`;
