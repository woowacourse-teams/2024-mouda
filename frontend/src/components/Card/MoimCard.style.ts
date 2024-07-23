import { common } from '@_common/common.style';
import { css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  gap: 0.32rem;
  align-items: flex-start;

  width: 100%;
  padding: 3.2rem 1.6rem;

  background: #ededed;
  border-radius: 2.5rem;
`;

export const cardTitle = css`
  ${common.fontType.title}
  font-size: 2.4rem;
`;

export const subjectTag = css`
  display: inline-flex;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  padding: 0.16rem 0.048rem;

  font-size: 1.2rem;
  font-weight: 600;
  font-style: normal;
  line-height: 130%;
  color: var(--main-blue, #477bff);

  background: #dde7ff;
  border-radius: 1rem;
`;

export const subjectBox = css`
  display: flex;
  gap: 0.32rem;
`;

export const subjectInfo = css`
  display: inline-block;
  font-size: 1.6rem;
  line-height: 130%;
  vertical-align: middle;
`;
