import { css, Theme } from '@emotion/react';

export const getTriSectionHeaderStyle = ({
  borderBottomColor,
}: {
  borderBottomColor?: string;
}) => {
  return css`
    position: relative;

    display: flex;
    align-items: center;
    justify-content: space-between;

    height: 5rem;
    margin: 0 2rem;

    ${borderBottomColor && `border-bottom: 1px solid ${borderBottomColor};`}

    & > * {
      position: absolute;
      display: flex;
      flex-direction: row;
    }
  `;
};

export const leftSectionStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5};
`;

export const centerSectionStyle = (props: { theme: Theme }) => css`
  left: 50%;
  transform: translateX(-50%);
  ${props.theme.typography.s1};
`;

export const rightSectionStyle = css`
  right: 0;
`;
