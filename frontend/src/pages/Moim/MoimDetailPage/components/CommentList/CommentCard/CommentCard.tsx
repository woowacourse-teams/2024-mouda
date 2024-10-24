import * as S from './CommentCard.style';

import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';

import { Comment } from '@_types/index';
import { HTMLProps } from 'react';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';
import { useTheme } from '@emotion/react';

export interface CommentCardProps extends HTMLProps<HTMLDivElement> {
  comment: Comment;
  onWriteClick?: () => void;
  isChecked?: boolean;
  isChild?: boolean;
}
const getDateTimeAriaLabel = (string: string) => {
  const [date, time] = string.split(' ');
  const dateLabel = formatYyyymmddToKorean(date, '-', true);
  const timeLabel = formatHhmmToKorean(time, ':');

  return `${dateLabel} ${timeLabel}`;
};

export default function CommentCard(props: CommentCardProps) {
  const {
    comment: { profile, nickname, dateTime, content, children },
    onWriteClick,
    isChecked = false,
    isChild = false,
  } = props;

  const { nicknameRef, formattedNickname } = useNicknameWidthEffect({
    nickname,
    maxNicknameWidth: 100,
  });

  const theme = useTheme();

  return (
    <ul css={S.commentContainer()}>
      <li css={S.commentWrapper({ theme, isChecked })}>
        <ProfileFrame
          width={3}
          height={3}
          borderWidth={0}
          src={profile}
          aria-hidden
        />

        <div css={S.commnetBox()}>
          <div
            css={S.commentLeft}
            aria-label={
              (isChild ? '답글 ' : '') +
              '작성자 ' +
              nickname +
              ' 작성시간 ' +
              getDateTimeAriaLabel(dateTime) +
              ' 내용 ' +
              content
            }
            tabIndex={0}
          >
            <div css={S.commentLeftHeader}>
              <div
                ref={nicknameRef}
                css={[S.commentNickname, theme.typography.small]}
                aria-hidden
              >
                {formattedNickname}
              </div>
              <div css={S.timestamp({ theme })} aria-hidden>
                {dateTime}
              </div>
            </div>
            <div css={S.contentBox({ theme })} aria-hidden>
              {content}
            </div>
          </div>
          {!isChild && (
            <div css={S.commentHeaderRight({ theme })}>
              <button onClick={onWriteClick} aria-label="답글쓰기">
                답글쓰기
              </button>
            </div>
          )}
        </div>
      </li>
      {children && (
        <div css={S.commentChildBox()}>
          {children.map((childComment) => (
            <CommentCard
              key={childComment.commentId}
              comment={childComment}
              isChild={true}
            />
          ))}
        </div>
      )}
    </ul>
  );
}
