import { css, useTheme } from '@emotion/react';
import { isBetChatRoomDetail, isMoimChatRoomDetail } from '@_types/typeGuards';
import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import CalenderClock from '@_components/Icons/CalenderClock';
import ChatBottomMenu from './components/ChatBottomMenu/ChatBottomMenu';
import ChatInfoAccordion from './components/ChatInfoAccordion/ChatInfoAccordion';
import ChatList from './components/ChatList/ChatList';
import ChatMenuItem from './components/ChatMenuItem/ChatMenuItem';
import ChattingFooter from './components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import ChattingRoomSidebar from './components/ChattingRoomSidebar/ChattingRoomSidebar';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import DateTimeModalContent from './components/DateTimeModalContent/DateTimeModalContent';
import GET_ROUTES from '@_common/getRoutes';
import Hamburger from '@_components/Icons/Hamburger';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import Modal from '@_components/Modal/Modal';
import Picker from '@_components/Icons/Picker';
import PlaceModalContent from './components/PlaceModalContent/PlaceModalContent';
import SolidArrow from '@_components/Icons/SolidArrow';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import useChatRoomDetail from '@_hooks/queries/useChatRoomDetail';
import useChats from '@_hooks/queries/useChats';
import useConfirmDateTime from '@_hooks/mutaions/useConfirmDatetime';
import useConfirmPlace from '@_hooks/mutaions/useConfirmPlace';
import useSendMessage from '@_hooks/mutaions/useSendMessage';

type ModalContent = 'place' | 'datetime';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();

  const chatRoomId = +(params.chatRoomId || '0');

  const { chatRoomDetail } = useChatRoomDetail(chatRoomId);
  const { chats } = useChats(chatRoomId);

  const { mutate: confirmDateTime, isPending: isPendingConfirmDateTime } =
    useConfirmDateTime();
  const { mutate: confirmPlace, isPending: isPendingConfirmPlacd } =
    useConfirmPlace();
  const { mutate: handleSendMessage, isPending: isPendingSendMessage } =
    useSendMessage(chatRoomId);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [nowModalContent, setNowModalContent] = useState<ModalContent>('place');

  const modal = useMemo(() => {
    if (!chatRoomDetail) return;
    if (!isMoimChatRoomDetail(chatRoomDetail)) return;
    if (nowModalContent === 'datetime')
      return (
        !isPendingConfirmDateTime && (
          <Modal onClose={() => setIsModalOpen(false)}>
            <DateTimeModalContent
              onCancel={() => setIsModalOpen(false)}
              onConfirm={({ date, time }: { date: string; time: string }) => {
                confirmDateTime({
                  chatRoomId: chatRoomDetail.chatRoomId,
                  date,
                  time,
                });
                setIsModalOpen(false);
              }}
            />
          </Modal>
        )
      );
    if (nowModalContent === 'place')
      return (
        !isPendingConfirmPlacd && (
          <Modal onClose={() => setIsModalOpen(false)}>
            <PlaceModalContent
              onCancel={() => setIsModalOpen(false)}
              onConfirm={(place: string) => {
                confirmPlace({ chatRoomId: chatRoomDetail.chatRoomId, place });
                setIsModalOpen(false);
              }}
            />
          </Modal>
        )
      );
  }, [
    chatRoomDetail,
    nowModalContent,
    confirmDateTime,
    confirmPlace,
    isPendingConfirmDateTime,
    isPendingConfirmPlacd,
  ]);

  const menuItems = useMemo(() => {
    if (!chatRoomDetail) {
      return (
        <MissingFallback
          text={'아직 데이터가 로드되기도 전인데... \n 정말 빨리 누르시는군요'}
          backgroundColor={theme.colorPalette.white[100]}
        />
      );
    }
    if (isMoimChatRoomDetail(chatRoomDetail)) {
      if (chatRoomDetail.attributes.isMoimer) {
        return (
          <ChatBottomMenu>
            <ChatMenuItem
              icon={<Picker />}
              description="장소 정하기"
              onClick={() => {
                setNowModalContent('place');
                setIsModalOpen(true);
              }}
            />
            <ChatMenuItem
              icon={<CalenderClock />}
              description="날짜/시간 정하기"
              onClick={() => {
                setNowModalContent('datetime');
                setIsModalOpen(true);
              }}
            />
          </ChatBottomMenu>
        );
      }
      return (
        <MissingFallback
          text={'모임에 참여한 사람은 \n 아직 할 수 있는 기능이 없어요'}
          backgroundColor={theme.colorPalette.white[100]}
        />
      );
    }

    if (isBetChatRoomDetail(chatRoomDetail)) {
      return (
        <MissingFallback
          text={'안내면진다 채팅방에서는 \n 아직 할 수 있는 기능이 없어요'}
          backgroundColor={theme.colorPalette.white[100]}
        />
      );
    }
    return (
      <MissingFallback
        text={'아직 데이터가 로드되지 않았는데... \n 정말 빨리 누르시는군요'}
        backgroundColor={theme.colorPalette.white[100]}
      />
    );
  }, [chatRoomDetail, theme]);

  return (
    <ChattingRoomLayout>
      <ChattingRoomLayout.Header>
        <ChattingRoomLayout.Header.Left>
          <SolidArrow
            direction="left"
            onClick={() =>
              navigate(GET_ROUTES.nowDarakbang.chat(), {
                state: { type: chatRoomDetail?.type },
              })
            }
          />
        </ChattingRoomLayout.Header.Left>
        <ChattingRoomLayout.Header.Center>
          <DarakbangNameWrapper font={theme.typography.s1}>
            {chatRoomDetail && chatRoomDetail.title}
          </DarakbangNameWrapper>
          <span
            css={[
              css`
                margin: 1px 0 0 2px;
              `,
              theme.coloredTypography.s1(theme.colorPalette.grey[400]),
            ]}
          >
            {chatRoomDetail && ' ' + chatRoomDetail.participations.length}
          </span>
        </ChattingRoomLayout.Header.Center>
        <ChattingRoomLayout.Header.Right>
          <Hamburger onClick={() => setIsSidebarOpen(true)} />
        </ChattingRoomLayout.Header.Right>
      </ChattingRoomLayout.Header>
      <ChattingRoomSidebar
        members={chatRoomDetail?.participations || []}
        isOpen={isSidebarOpen}
        onClose={() => setIsSidebarOpen(false)}
      />
      <ChattingRoomLayout.HeaderBottom>
        {chatRoomDetail && isMoimChatRoomDetail(chatRoomDetail) && (
          <ChatInfoAccordion
            tagTheme={chatRoomDetail.attributes.isStarted ? 'yellow' : 'orange'}
            tagValue={
              chatRoomDetail.attributes.isStarted ? '모임 후' : '모임 중'
            }
            textList={[
              `${(chatRoomDetail.attributes.date || '') && chatRoomDetail.attributes.date.replaceAll('-', '.')}
  ${(chatRoomDetail.attributes.time || '') && ' ' + formatHhmmToKoreanWithPrefix(chatRoomDetail.attributes.time)}`,
              `${chatRoomDetail.attributes.place || ''}`,
            ]}
          />
        )}
        {chatRoomDetail && isBetChatRoomDetail(chatRoomDetail) && (
          <ChatInfoAccordion
            tagTheme={chatRoomDetail.attributes.isLoser ? 'yellow' : 'orange'}
            tagValue={chatRoomDetail.attributes.isLoser ? '당첨!' : '미당첨'}
            textList={
              chatRoomDetail.attributes.isLoser
                ? ['당첨입니다!', '혹시 안내셨나요?ㅋ']
                : [
                    '살아남았습니다!',
                    `당첨된 사람은 ${chatRoomDetail.attributes.loser.nickname}입니다!`,
                  ]
            }
          />
        )}
      </ChattingRoomLayout.HeaderBottom>
      <ChatList chats={chats} />
      <ChattingRoomLayout.Footer>
        <ChattingFooter
          onSubmit={handleSendMessage}
          disabled={isPendingSendMessage}
          onMenuClick={() => setIsMenuOpen(!isMenuOpen)}
          onTextAreaFocus={() => {
            setIsMenuOpen(false);
          }}
        />
        {isMenuOpen && menuItems}
      </ChattingRoomLayout.Footer>

      {isModalOpen && modal}
    </ChattingRoomLayout>
  );
}
