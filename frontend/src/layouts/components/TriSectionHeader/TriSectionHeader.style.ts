import { css } from '@emotion/react';

export const getTriSectionHeaderStyle = ({
  borderBottomColor,
}: {
  borderBottomColor?: string;
}) => {
  return css`
    position: relative;

    height: 53px;

    display: flex;
    align-items: center;
    justify-content: space-between;

    ${borderBottomColor && `border-bottom: 1px solid ${borderBottomColor};`}

    & > * {
      position: absolute;
      display: flex;
      flex-direction: row;
    }
  `;
};

export const leftSectionStyle = css`
  left: 1rem;
`;

export const centerSectionStyle = css`
  left: 50%;
  transform: translateX(-50%);
`;

export const rightSectionStyle = css`
  right: 1rem;
`;
