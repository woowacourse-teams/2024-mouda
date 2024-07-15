import { css } from '@emotion/react';
import { common } from '../../common/common.style';

export const required = css`
  color: #f00;
`;

export const title = css`
  ${common.fontType.subtitle};
  margin: 0 0 10px 0;
`;

export const input = css`
  width: 100%;
  font-size: 1rem;
  height: 2.5rem;
  flex-shrink: 0;
  border-radius: 0.5rem;
  border: 1px solid #b3b3b3;

  background: #fff;
`;
