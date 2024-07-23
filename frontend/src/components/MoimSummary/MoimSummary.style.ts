import { common } from '@_common/common.style';
import { css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const titleStyle = css`
  ${common.fontType.title}
`;

export const contentStyle = css`
  display: flex;
  gap: 6px;
  align-items: center;
`;

export const contentKeyStyle = css`
  padding: 2px 6px;

  font-size: 12px;
  font-weight: 600;
  color: rgb(71 123 255 / 100%);

  background: rgb(221 231 255 / 100%);
  border-radius: 10px;
`;

export const contentValueStyle = css`
  font-size: 14px;
  font-weight: 500;
`;
