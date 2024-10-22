import * as S from './RouletteWrapper.style';

import { PropsWithChildren, useEffect, useRef, useState } from 'react';

import { useTheme } from '@emotion/react';

interface RouletteWrapperProps extends PropsWithChildren {
  title: string;
  description: string;
  announceDate: Date;
}

export default function RouletteWrapper(props: RouletteWrapperProps) {
  const theme = useTheme();
  const { title, description, announceDate, children } = props;
  const [timeString, setTimeString] = useState('00:00');
  //@ts-expect-error Date 객체 뺄셈
  const leftSecond = useRef(Math.floor((announceDate - new Date()) / 1000));

  useEffect(() => {
    //@ts-expect-error Date 객체 뺄셈
    leftSecond.current = Math.floor((announceDate - new Date()) / 1000);
    const intervalId = setInterval(() => {
      leftSecond.current--;
      if (leftSecond.current < 0) {
        setTimeString('GO GO!!');
        return;
      }

      setTimeString(
        `${Math.floor(leftSecond.current / 60)
          .toString()
          .padStart(2, '00')}:${(leftSecond.current % 60)
          .toString()
          .padStart(2, '00')}`,
      );
    }, 1000);

    return () => clearInterval(intervalId);
  }, [announceDate]);

  return (
    <div css={S.container({ theme })}>
      <h2 css={S.title({ theme })}>{title}</h2>
      <div css={S.descriptionBox({ theme })}>
        <div css={S.descriptionWrapper}>
          {description.split('*').map((str, index) => (
            <span
              key={`${str}-${index}`}
              css={index % 2 ? theme.typography.h5 : theme.typography.b4}
            >
              {str}
            </span>
          ))}
        </div>
      </div>
      <span css={S.time({ theme })}>{timeString}</span>
      <div css={S.rouletteContainer({ theme })}>{children}</div>
    </div>
  );
}
