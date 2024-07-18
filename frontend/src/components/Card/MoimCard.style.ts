import { common } from '@_common/common.style';
import { css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  align-items: flex-start;
  gap: 0.2rem;
  flex-direction: column;
  width: 100%;
  padding: 2rem 1rem;
  flex-shrink: 0;
  border-radius: 1.5625rem;
  background: #ededed;
`;

export const cardTitle = css`
  ${common.fontType.title}
  font-size: 1.5rem;
`;

export const subjectTag = css`
  display: inline-flex;
  padding: 0.1rem 0.3rem;
  justify-content: center;
  align-items: center;
  gap: 0.625rem;
  border-radius: 0.625rem;
  background: #dde7ff;
  color: var(--Main-Blue, #477bff);
  font-family: Pretendard;
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 600;
  line-height: 130%;
`;

export const subjectBox = css`
  display: flex;
  gap: 0.2rem;
`;

export const subjectInfo = css`
  line-height: 130%;
  display: inline-block;
  vertical-align: middle;
`;
