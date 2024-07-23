import { common } from '@_common/common.style';
import { css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  gap: 0.2rem;
  align-items: flex-start;

  width: 100%;
  padding: 2rem 1rem;

  background: #ededed;
  border-radius: 1.5625rem;
`;

export const cardTitle = css`
  ${common.fontType.title}
  font-size: 1.5rem;
`;

export const subjectTag = css`
  display: inline-flex;
  gap: 0.625rem;
  align-items: center;
  justify-content: center;

  padding: 0.1rem 0.3rem;

  font-size: 0.75rem;
  font-weight: 600;
  font-style: normal;
  line-height: 130%;
  color: var(--main-blue, #477bff);

  background: #dde7ff;
  border-radius: 0.625rem;
`;

export const subjectBox = css`
  display: flex;
  gap: 0.2rem;
`;

export const subjectInfo = css`
  display: inline-block;
  line-height: 130%;
  vertical-align: middle;
`;
