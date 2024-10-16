import { useEffect, useRef, useState } from 'react';

export default function useNicknameWidthEffect({
  nickname,
  maxNicknameWidth,
}: {
  nickname: string;
  maxNicknameWidth: number;
}) {
  const nicknameRef = useRef<HTMLDivElement>(null);
  const [formattedNickname, setFormattedNickname] = useState(nickname);

  useEffect(() => {
    const nicknameEl = nicknameRef.current;
    if (!nicknameEl) {
      return;
    }

    const nicknameWidth = nicknameEl.offsetWidth;
    if (nicknameWidth > maxNicknameWidth) {
      const midpoint = Math.ceil(nickname.length / 2);
      const firstPart = nickname.slice(0, midpoint);
      const secondPart = nickname.slice(midpoint);
      setFormattedNickname(`${firstPart}\n${secondPart}`);
    }
  }, [nickname, maxNicknameWidth]);

  return {
    nicknameRef,
    formattedNickname,
  };
}
