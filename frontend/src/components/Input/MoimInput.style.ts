import { common } from '@_common/common.style';
import { css } from '@emotion/react';

export const required = css`
  color: #f00;
`;

export const title = css`
  ${common.fontType.subtitle};
  margin: 0 0 10px;
`;

export const input = css`
  flex-shrink: 0;

  width: 100%;
  height: 4rem;

  font-size: 1.6rem;

  background: #fff;
  border: 1px solid #b3b3b3;
  border-radius: 0.8rem;
`;
