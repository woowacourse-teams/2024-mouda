import { Theme, css } from '@emotion/react';

export const footer = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-around;

  padding: 1.3rem 0;

  background-color: ${theme.colorPalette.white[100]};
  box-shadow: 0 -10px 15px rgb(0 0 0 / 20%);
`;

export const menuButton = ({ theme }: { theme: Theme }) => css`
  width: 4.5rem;
  height: 4.5rem;

  background-color: ${theme.colorPalette.orange[300]};
  border: 0;
  border-radius: 50%;
`;

export const messageForm = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 80%;
  max-height: 4rem;
  padding: 1rem 2rem;

  background: ${theme.colorPalette.grey[100]};
  border-radius: 50px;
`;

export const messageTextArea = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.s2};
  resize: none;

  width: 100%;

  background: ${theme.colorPalette.grey[100]};
  border: 0;
  outline: none;

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const sendingButton = css`
  background: rgb(0 0 0 / 0%);
  border: 0;
`;
