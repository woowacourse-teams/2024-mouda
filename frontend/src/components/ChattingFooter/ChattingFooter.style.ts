import { Theme, css } from '@emotion/react';

export const footer = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-around;

  height: 7rem;

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
  height: 70%;
  padding: 1rem 2rem;

  background: ${theme.colorPalette.grey[200]};
  border-radius: 50px;
`;

export const messageInput = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.s2}
  width: 100%;
  background: rgb(0 0 0 / 0%);
  border: 0;
  outline: none;
`;

export const sendingButton = css`
  background: rgb(0 0 0 / 0%);
  border: 0;
`;
