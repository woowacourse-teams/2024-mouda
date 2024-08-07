import { css, Theme } from '@emotion/react';

export const cardBox = css`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;

  width: 100%;
  padding: 2rem 2.4rem;

  background: white;
  border-radius: 2.5rem;
  box-shadow: rgb(0 0 0 / 24%) 0 3px 8px;
`;

export const titleBox = css`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  justify-content: space-between;
`;

export const cardTitle = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    ${theme.typography.s1}
  `;
};

export const heartButton = css`
  padding: 0;
  background: none;
  border: 0;
`;

export const subjectBox = css`
  display: flex;
  gap: 0.8rem;
`;

export const subjectTag = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    ${theme.typography.small}
    display: inline-flex;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${theme.colorPalette.orange[300]};

    background: ${theme.colorPalette.yellow[50]};
    border-radius: 1rem;
  `;
};

export const subjectInfo = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    ${theme.typography.b3}
    display: inline-block;
  `;
};

export const detailInfo = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    display: flex;
    flex-direction: column;
    gap: 0.2rem;
    color: ${theme.colorPalette.black[30]};
  `;
};
