import { css, Theme } from '@emotion/react';

export const commentContainer = () => css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
export const commentWrapper = () => css`
  display: flex;
  align-items: center;
  width: 100%;
  background-color: #f3f4f6;
`;

export const profileImage = () => css`
  width: 3rem;
  height: 3rem;
`;

export const commnetBox = () => css`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-left: 1rem;
`;
export const commnetHeader = () => css`
  display: flex;
  gap: 5px;
  align-items: center;
  justify-content: space-between;

  width: 100%;
`;

export const commentHeaderLeft = () => css`
  display: flex;
  gap: 0.7rem;
  align-items: center;
`;

export const commentHeaderRight = (props: { theme: Theme }) => css`
  display: flex;
  align-items: center;

  button {
    ${props.theme.typography.c3}
    color: ${props.theme.colorPalette.grey[500]};
    border: none;
  }

  button:hover {
    ${props.theme.typography.c3}
    color: ${props.theme.colorPalette.grey[700]};
    background-color: ${props.theme.colorPalette.grey[400]};
    border: none;
  }
`;
export const contentBox = (props: { theme: Theme }) => css`
  ${props.theme.typography.c2}
`;

export const timestamp = (props: { theme: Theme }) => css`
  ${props.theme.typography.c3}
  color: ${props.theme.colorPalette.grey[500]};
`;
export const commentChildBox = () => css`
  margin-left: 3rem;
`;
