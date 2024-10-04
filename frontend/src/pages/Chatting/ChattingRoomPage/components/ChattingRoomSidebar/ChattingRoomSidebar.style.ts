import { css } from '@emotion/react';

export const sidebar = css`
  position: absolute;
  right: 0;

  overflow-y: scroll;

  width: 80%;
  height: 100vh;

  background-color: white;
`;

export const sidebarHeaderWrapper = css`
  display: flex;
  align-items: center;
  justify-content: space-around;

  width: 100%;
  height: 10%;
`;

export const sidebarHeader = css`
  display: inline-block;
`;

export const dimmer = css`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  background-color: rgb(0 0 0 / 23%);
`;

export const members = css`
  display: flex;
  flex-direction: column;
  gap: 0;
  margin: 0 2rem;
`;

export const memberWrapper = css`
  display: flex;
  width: 100%;
  height: 5rem;
`;
