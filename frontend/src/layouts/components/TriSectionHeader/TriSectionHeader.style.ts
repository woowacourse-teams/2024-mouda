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
    ${borderBottomColor && `border-bottom: 1px solid ${borderBottomColor};`}

    & > * {
      position: absolute;
      display: flex;
      flex-direction: row;
    }
  `;
};

export const leftSectionStyle = (props: { theme: Theme }) => css`
  left: 1.6rem;
  font-size: ${props.theme.typography.h5};
`;

export const centerSectionStyle = css`
  left: 50%;
  transform: translateX(-50%);
`;

export const rightSectionStyle = css`
  right: 1.6rem;
`;
